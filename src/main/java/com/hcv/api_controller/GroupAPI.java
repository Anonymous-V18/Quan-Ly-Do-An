package com.hcv.api_controller;

import com.hcv.dto.request.GroupInput;
import com.hcv.dto.request.ShowAllRequest;
import com.hcv.dto.response.ApiResponse;
import com.hcv.dto.response.GroupDTO;
import com.hcv.dto.response.GroupResponse;
import com.hcv.dto.response.ShowAllResponse;
import com.hcv.service.IGroupService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/groups")
public class GroupAPI {

    IGroupService groupService;

    @PostMapping("/insert")
    @PreAuthorize("hasRole('STUDENT')")
    public ApiResponse<GroupDTO> insert(@RequestBody GroupInput groupInput) {
        GroupDTO groupDTO = groupService.insert(groupInput);
        return ApiResponse.<GroupDTO>builder()
                .result(groupDTO)
                .build();
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('STUDENT')")
    public ApiResponse<GroupDTO> update(@PathVariable(value = "id") String id,
                                        @RequestBody GroupInput groupInput) {
        GroupDTO updateDTO = groupService.update(id, groupInput);
        return ApiResponse.<GroupDTO>builder()
                .result(updateDTO)
                .build();
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('STUDENT')")
    public ApiResponse<String> delete(@RequestBody String[] ids) {
        groupService.delete(ids);
        return ApiResponse.<String>builder()
                .message("Xóa nhóm thành công !")
                .build();
    }

    @GetMapping("/showAll")
    public ApiResponse<ShowAllResponse<GroupResponse>> showAll(@RequestParam(name = "page") Integer page,
                                                               @RequestParam(name = "limit") Integer limit,
                                                               @RequestParam(name = "orderBy") String orderBy,
                                                               @RequestParam(name = "orderDirection") String orderDirection) {
        ShowAllRequest showAllRequest = ShowAllRequest.builder()
                .page(page)
                .limit(limit)
                .orderBy(orderBy)
                .orderDirection(orderDirection)
                .build();
        ShowAllResponse<GroupResponse> response = groupService.showAll(showAllRequest);
        return ApiResponse.<ShowAllResponse<GroupResponse>>builder()
                .result(response)
                .build();
    }

}
