package com.ys.stock.service.impl;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.ys.stock.common.domain.UrlDomain;
import com.ys.stock.exception.BusinessException;
import com.ys.stock.mapper.SysUserMapper;
import com.ys.stock.service.UpLoadService;
import com.ys.stock.utils.PathUtils;
import com.ys.stock.vo.resp.R;
import com.ys.stock.vo.resp.ResponseCode;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.InputStream;

/**
 * @author 杨森
 * @version 1.0
 * @date 2023/9/7 10:45
 */
@Service
@Data
@ConfigurationProperties(prefix = "oss")
public class UpLoadServiceImpl implements UpLoadService {

    @Resource
    private SysUserMapper sysUserMapper;

    @Override
    public R uploadImg(MultipartFile img) {
        //判断文件类型
        //获取原始文件名
        String originalFilename = img.getOriginalFilename();
        //对原始文件名进行判断
        if(!originalFilename.endsWith(".png")&&!originalFilename.endsWith(".jpg")&&!originalFilename.endsWith("JPEG")){
            throw new BusinessException(ResponseCode.UPLOAD_IMG_FAIL.getMessage());
        }
        //如果判断通过上传文件到OSS
        String filePath = PathUtils.generateFilePath(originalFilename);
        String url = uploadOss(img,filePath);//  2099/2/3/wqeqeqe.png
        return R.ok(ResponseCode.UPLOAD_SUCCESS.getCode(),ResponseCode.UPLOAD_SUCCESS.getMessage(),url);
    }



    private String accessKey;
    private String secretKey;
    private String bucket;

    private String uploadOss(MultipartFile imgFile, String filePath) {

        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.autoRegion());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = filePath;
        try {
            InputStream inputStream = imgFile.getInputStream();
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);
            try {
                Response response = uploadManager.put(inputStream,key,upToken,null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                System.out.println(putRet.key);
                System.out.println(putRet.hash);
                String resKey = "http://rz7ctb453.hd-bkt.clouddn.com/"+key;
                return resKey;
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (Exception ex) {
            //ignore
        }
        return "www";
    }

    @Override
    public R updateUrl(UrlDomain urlDomain) {
        String url = urlDomain.getUrl();
        System.out.println("url:" +url);
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        String userId = sysUserMapper.getUserId(userName);
        System.out.println("userName:"+userName);
        System.out.println("userId:"+userId);

//        int res = sysUserMapper.updateAvatar(url,userId);
        sysUserMapper.updateAvatar(url,userId);
//        if (!(Integer.valueOf(res) == MagicValue.SUCCESSVAL)){
//            throw new BusinessException(ResponseCode.UPLOAD_FAIL.getMessage());
//        }
        return R.ok();

    }
}
