/**
 * Created by 小机灵鬼儿
 * 2022/2/28 7:14
 */

package com.exam.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.exam.demo.entity.Onteach;
import com.exam.demo.mapper.OnteachMapper;
import com.exam.demo.service.OnteachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: wxn
 * @Date: 2022/2/28
 */
@Service
public class OnteachServiceImpl implements OnteachService {

    @Autowired
    OnteachMapper onteachMapper;
    @Override
    public List<Onteach> selectAll() {
        return onteachMapper.selectList(new LambdaQueryWrapper<>());
    }

    @Override
    public int insert(Onteach onteach) {
        return onteachMapper.insert(onteach);
    }

    @Override
    public int update(Onteach onteach) {
        return onteachMapper.updateById(onteach);
    }

    @Override
    public int delete(int subject_id) {
        return onteachMapper.deleteById(subject_id);
    }
}
