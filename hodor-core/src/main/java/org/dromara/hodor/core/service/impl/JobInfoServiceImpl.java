package org.dromara.hodor.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hodor.core.entity.JobInfo;
import org.dromara.hodor.core.enums.JobStatus;
import org.dromara.hodor.core.mapper.JobInfoMapper;
import org.dromara.hodor.core.service.JobInfoService;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

/**
 * job info service
 *
 * @author tomgs
 * @since 2020/6/30
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class JobInfoServiceImpl implements JobInfoService {

    private final @NonNull JobInfoMapper jobInfoMapper;

    @Override
    public void addJob(JobInfo jobInfo) {
        jobInfoMapper.insert(jobInfo);
    }

    @Override
    public Integer queryAssignableJobCount() {
        return jobInfoMapper.selectCount(Wrappers.<JobInfo>lambdaQuery()
            .eq(JobInfo::getJobStatus, JobStatus.READY)
            .or()
            .eq(JobInfo::getJobStatus, JobStatus.RUNNING));
    }

    @Override
    public Long queryJobHashIdByOffset(Integer offset) {
        // select hash_id from hodor_job_info order by hash_id limit ${offset}, 1;
        QueryWrapper<JobInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("hash_id").orderByAsc("hash_id").last(String.format("limit %s, 1", offset));
        JobInfo hodorJobInfo = jobInfoMapper.selectOne(queryWrapper);
        return hodorJobInfo == null ? -1 : hodorJobInfo.getHashId();
    }

    @Override
    public Long queryJobIdByOffset(Integer offset) {
        // select id from hodor_job_info order by id limit ${offset}, 1;
        QueryWrapper<JobInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id").orderByAsc("id").last(String.format("limit %s, 1", offset));
        JobInfo hodorJobInfo = jobInfoMapper.selectOne(queryWrapper);
        return hodorJobInfo == null ? -1 : hodorJobInfo.getId();
    }

    @Override
    public List<JobInfo> queryJobInfoByHashIdOffset(Long startHashId, Long endHashId) {
        return jobInfoMapper.selectList(Wrappers.<JobInfo>lambdaQuery()
            .ge(JobInfo::getHashId, startHashId)
            .lt(JobInfo::getHashId, endHashId));
    }

}
