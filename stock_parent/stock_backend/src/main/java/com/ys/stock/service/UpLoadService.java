package com.ys.stock.service;

import com.ys.stock.common.domain.UrlDomain;
import com.ys.stock.vo.resp.R;
import org.springframework.web.multipart.MultipartFile;

public interface UpLoadService {

    R uploadImg(MultipartFile img);

    R updateUrl(UrlDomain urlDomain);

}
