package com.pjwards.aide.service.assets;


import com.pjwards.aide.domain.Assets;
import com.pjwards.aide.exception.AssetsNotFoundException;

import java.util.List;

public interface AssetsService {
    public List<Assets> findAll();

    public Assets add(Assets added);

    public Assets findById(Long id) throws AssetsNotFoundException;

    public Assets update(Assets updated) throws AssetsNotFoundException;

    public Assets deleteById(Long id) throws AssetsNotFoundException;
}
