package com.ys.stock.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import com.ys.stock.constants.MagicValue;
import com.ys.stock.exception.BusinessException;
import com.ys.stock.mapper.SysRoleMapper;
import com.ys.stock.mapper.SysRolePermissionMapper;
import com.ys.stock.mapper.SysUserMapper;
import com.ys.stock.mapper.SysUserRoleMapper;
import com.ys.stock.pojo.SysPermission;
import com.ys.stock.pojo.SysRole;
import com.ys.stock.pojo.SysUser;
import com.ys.stock.pojo.SysUserRole;
import com.ys.stock.service.PermissionService;
import com.ys.stock.service.UserService;
import com.ys.stock.utils.IdWorker;
import com.ys.stock.vo.req.*;
import com.ys.stock.vo.resp.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author 杨森
 * @description: 定义服务接口实现
 * @date 2023年06月15日 22:01
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Autowired
    private SysRolePermissionMapper sysRolePermissionMapper;



    @Override
    public R<LoginRespVo> login(LoginReqVo vo) {
        //判断 VO是否存在 或者 用户名是否存在 或者 密码是否存在
        if (vo == null || Strings.isNullOrEmpty(vo.getUsername())
                || Strings.isNullOrEmpty(vo.getPassword()) || Strings.isNullOrEmpty(vo.getRkey())) {
            return R.error(ResponseCode.DATA_ERROR.getMessage());
        }
        //验证码校验
        //获取redis中rkey对应的code验证码
        String rCode = (String) redisTemplate.opsForValue().get(vo.getRkey());
        //校验
        if (Strings.isNullOrEmpty(rCode) || !rCode.equals(vo.getCode())) {
            return R.error(ResponseCode.DATA_ERROR.getMessage());

        }
        //redis清除key
        redisTemplate.delete(vo.getRkey());

        //根据用户名查询用户信息
        SysUser user = this.sysUserMapper.findByUserName(vo.getUsername());
        //判断用户是否存在，存在则密码校验比对
        if (user == null || !passwordEncoder.matches(vo.getPassword(), user.getPassword())) {
            return R.error(ResponseCode.SYSTEM_PASSWORD_ERROR.getMessage());

        }
        //组装登录成功数据
        LoginRespVo respVo = new LoginRespVo();
        //属性名称与类型必须相同，否则copy不到
        BeanUtils.copyProperties(user, respVo);
        //获取指定用户的权限集合
        List<SysPermission> permissions = permissionService.getPermissionByUserId(user.getId());
        //获取树状权限菜单数据
        List<PermissionRespNodeVo> tree = permissionService.getTree(permissions,"0",true);
        List<String> authBtnPerms = permissions.stream()
                .filter(per->!Strings.isNullOrEmpty(per.getCode())&&per.getType()==3)
                .map(per->per.getCode()).collect(Collectors.toList());
        respVo.setMenus(tree);
        respVo.setPermissions(authBtnPerms);
        return R.ok(respVo);
    }

    @Override
    public R<Map> generateCaptcha() {
        //1.生成4位数字验证码
        String checkCode = RandomStringUtils.randomNumeric(4);
        //2.获取全局唯一id
        String rkey = String.valueOf(idWorker.nextId());
        //验证码存入redis中，并设置有效期1分钟
        redisTemplate.opsForValue().set(rkey, checkCode, 60, TimeUnit.SECONDS);
        //3.组装数据
        HashMap<String, String> map = new HashMap<>();
        map.put("rkey", rkey);
        map.put("code", checkCode);
        return R.ok(map);
    }

    /**
     * 用户管理多条件查询
     *
     * @param usersReqVo 查询条件
     * @return
     */
    @Override
    public R<PageResult<Rows>> MoreQueryUsers(QueryConditionsUsersReqVo usersReqVo) {
        //设置分页参数
        PageHelper.startPage(usersReqVo.getPageNum(), usersReqVo.getPageSize());
        List<Rows> userinfo;
        //根据传入的参数进行查询
        //
        if (Strings.isNullOrEmpty(usersReqVo.getUsername()) || Strings.isNullOrEmpty(usersReqVo.getNickName()) || Strings.isNullOrEmpty(usersReqVo.getStartTime()) || Strings.isNullOrEmpty(usersReqVo.getEndTime())) {
            userinfo = sysUserMapper.MoreQueryAll();
            System.out.println(userinfo);

        } else {
            //根据传入的条件查询所有的用户
            userinfo = sysUserMapper.MoreQueryUsers(usersReqVo.getUsername(), usersReqVo.getNickName(), usersReqVo.getStartTime(), usersReqVo.getEndTime());
        }
        //判断得到数据后是否为空数据
        if (CollectionUtils.isEmpty(userinfo)) {
            return R.ok(ResponseCode.NO_RESPONSE_DATA.getMessage());
        }
        //把得到的数据封装到PageInfo里面
        PageInfo<Rows> pageInfo = new PageInfo<>(userinfo);
        //把数据封装到pageResult里面
        PageResult<Rows> pageResult = new PageResult<>(pageInfo);
        return R.ok(pageResult);
    }

    /**
     * 添加用户
     *
     * @param vo 要添加的用户对象
     * @return
     */
    @Override
    public R<String> AddUser(UserAddReqVo vo) {
        //1.判断当前账户username是否已被使用
        SysUser dbUser= this.sysUserMapper.findUserByUserName(vo.getUsername());
        if (dbUser!=null) {
            return R.error(ResponseCode.ACCOUNT_EXISTS_ERROR.getMessage());
        }
        //2.否则添加
        //封装用户信息
        SysUser user = new SysUser();
        BeanUtils.copyProperties(vo,user);
        //设置用户id
        user.setId(idWorker.nextId().toString());
        //密码加密
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        //设置添加时间和更新时间
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        //是否删除
        user.setDeleted(MagicValue.STATUS_NOT_DEL);
        int count = this.sysUserMapper.insert(user);
        if (count!=MagicValue.SUCCESSVAL) {
            return R.error(ResponseCode.ERROR.getMessage());
        }
        return R.ok(ResponseCode.SUCCESS.getMessage());
    }


    /**
     * 更新用户角色信息
     *
     * @param vo
     * @return
     */

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> UpdateRloesOfUser(UserOwnRoleReqVo vo) {
        //判断用户iD是否存在
        if (vo.getUserId() == null) {
            return R.error(ResponseCode.DATA_ERROR.getMessage());
        }
        //删除用户原来所拥有的角色ID
        sysUserRoleMapper.deleteByUserId(vo.getUserId());
        //如果对应的集合为空，则说明用户将所有的角色都清除了
        if (CollectionUtils.isEmpty(vo.getRoleIds())) {
            return R.ok(ResponseCode.SUCCESS.getMessage());
        }
        //封装用户角色对象集合
        List<SysUserRole> list = vo.getRoleIds().stream().map(roleId -> {
            SysUserRole userRloe = SysUserRole.builder().userId(vo.getUserId()).roleId(roleId).createTime(new Date()).id(idWorker.nextId() + "").build();
            return userRloe;
        }).collect(Collectors.toList());
        //批量插入
        int count = sysUserRoleMapper.insertBatch(list);
        if (count == 0) {
            return R.error(ResponseCode.ERROR.getMessage());
        }
        return R.ok(ResponseCode.SUCCESS.getMessage());
    }

    /**
     * 批量删除用户
     *
     * @param userIds
     * @return
     */
    @Override
    public R<String> deleteUsers(List<String> userIds) {
        //判断集合是否空
        if (CollectionUtils.isEmpty(userIds)) {
            return R.error(ResponseCode.DATA_ERROR.getMessage());
        }
        int result = sysUserMapper.updateUserStatus4Deleted(userIds);
        if (result == 0) {
            return R.error(ResponseCode.DATA_ERROR.getMessage());
        }

        return R.ok(ResponseCode.SUCCESS.getMessage());
    }

    /**
     * 根据用户id查询用户信息
     *
     * @param id
     * @return
     */
    @Override
    public R<SysUser> getUserInfoById(String id) {
        SysUser userInfo = sysUserMapper.getUserInfoById(id);
        return R.ok(userInfo);
    }

    /**
     * 根据用户Id进行更新
     *
     * @param vo
     * @return
     */
    @Override
    public R<String> updateUserInfo(UserEditReqVO vo) {
        SysUser user = new SysUser();
        //设置更新者ID
        user.setUpdateId(vo.getId());
        //设置更新时间
        user.setUpdateTime(new Date());
        BeanUtils.copyProperties(vo, user);
        int result = sysUserMapper.updateByPrimaryKeySelective(user);
        if (result != MagicValue.SUCCESSVAL) {
            return R.error(ResponseCode.ERROR.getMessage());
        }
        return R.ok(ResponseCode.SUCCESS.getMessage());
    }

    @Override
    public R<UserOwnRoleRespVo> getRolesOfUserInfo(String userId) {
        //1.获取当前用户所拥有的角色id集合
        List<String> roleIds= this.sysUserRoleMapper.findRoleIdsByUserId(userId);
        //2.获取所有角色信息
        List<SysRole> roles= this.sysRoleMapper.findAll(MagicValue.STATUS_NOT_DEL);
        //3.封装数据
        UserOwnRoleRespVo vo = new UserOwnRoleRespVo();
        vo.setOwnRoleIds(roleIds);
        vo.setAllRole(roles);
        return R.ok(vo);
    }

    @Override
    public R<String> updatePasById(String id) {
        String pwd = passwordEncoder.encode(MagicValue.PASSWORD);
        int count = sysUserMapper.updatePasById(id,pwd);
        if(count != MagicValue.SUCCESSVAL){
            return R.error(ResponseCode.ERROR.getMessage());
        }
        return R.ok(ResponseCode.SUCCESS.getMessage());
    }

    @Override
    public R<String> updatePas(UpdatePasVo updatePasVo) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = null;
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
             currentUserName= authentication.getName();
//            System.out.println( currentUserName);
        }

        String pwd = sysUserMapper.selectByName(currentUserName);
        if (!passwordEncoder.matches(updatePasVo.oldPwd,pwd)){
            throw new BusinessException(ResponseCode.ERROR.getMessage());
        }

        String newPwd = passwordEncoder.encode(updatePasVo.newPwd);
        int count = sysUserMapper.updatePas(currentUserName,newPwd);
        System.out.println(count);
        if(count != MagicValue.SUCCESSVAL){
            return R.error(ResponseCode.ERROR.getMessage());
        }
        return R.ok(ResponseCode.SUCCESS.getMessage());
    }


}
