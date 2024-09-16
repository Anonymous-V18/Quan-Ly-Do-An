package com.hcv.service;

import com.hcv.dto.request.GroupInput;
import com.hcv.dto.request.GroupInsertInput;
import com.hcv.dto.request.ShowAllRequest;
import com.hcv.dto.response.GroupDTO;
import com.hcv.dto.response.GroupResponse;
import com.hcv.dto.response.ShowAllResponse;

public interface IGroupService {

    GroupDTO insert(GroupInsertInput groupInsertInput);

    void addMember(String leaderGroupId);

    void removeMember(String idOldGroup, GroupInput groupUpdateInput);

    void delete(String[] ids);

    GroupResponse showInfoMyGroup();

    ShowAllResponse<GroupResponse> showAllMyGroup(ShowAllRequest showAllRequest);

}
