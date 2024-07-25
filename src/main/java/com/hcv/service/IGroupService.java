package com.hcv.service;

import com.hcv.dto.GroupDTO;
import com.hcv.dto.request.GroupInput;
import com.hcv.dto.request.ShowAllRequest;
import com.hcv.dto.response.ShowAllResponse;

public interface IGroupService {

    GroupDTO insert(GroupInput groupInput);

    GroupDTO update(String oldGroupID, GroupInput newGroupDTO);

    void delete(String[] ids);

    int countAll();

    GroupDTO findOneById(String id);

    ShowAllResponse<GroupDTO> showAll(ShowAllRequest showAllRequest);
}
