package com.hcv.service;

import com.hcv.dto.GroupDTO;
import com.hcv.dto.request.ShowAllRequest;
import com.hcv.dto.request.group.GroupInput;
import com.hcv.dto.response.GroupResponse;
import com.hcv.dto.response.ShowAllResponse;

public interface IGroupService {

    GroupDTO insert(GroupInput groupInput);

    GroupDTO update(String oldGroupId, GroupInput newGroupInput);

    void delete(String[] ids);

    int countAll();

    GroupDTO findOneById(String id);

    ShowAllResponse<GroupResponse> showAll(ShowAllRequest showAllRequest);

}
