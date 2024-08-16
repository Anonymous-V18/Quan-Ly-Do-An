package com.hcv.api_controller;

import com.hcv.dto.request.GroupInput;
import com.hcv.dto.request.GroupInsertInput;
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
public class GroupController {

    IGroupService groupService;

    @PostMapping("/insert")
    @PreAuthorize("hasRole('STUDENT')")
    public ApiResponse<GroupDTO> insert(@RequestBody GroupInsertInput request) {
        GroupDTO response = groupService.insert(request);
        return ApiResponse.<GroupDTO>builder()
                .result(response)
                .build();
    }

    @PutMapping("/remove-member/{id}")
    @PreAuthorize("hasRole('STUDENT')")
    public ApiResponse<String> removeMember(@PathVariable("id") String id,
                                            @RequestBody GroupInput groupInput) {
        groupService.removeMember(id, groupInput);
        return ApiResponse.<String>builder()
                .message("Xóa thành viên thành công !")
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

    @GetMapping("/showAll-my-group")
    @PreAuthorize("hasRole('TEACHER')")
    public ApiResponse<ShowAllResponse<GroupResponse>> showAllMyGroup(@RequestParam(name = "page") Integer page,
                                                                      @RequestParam(name = "limit") Integer limit,
                                                                      @RequestParam(name = "orderBy") String orderBy,
                                                                      @RequestParam(name = "orderDirection") String orderDirection) {
        ShowAllRequest showAllRequest = ShowAllRequest.builder()
                .page(page)
                .limit(limit)
                .orderBy(orderBy)
                .orderDirection(orderDirection)
                .build();
        ShowAllResponse<GroupResponse> response = groupService.showAllMyGroup(showAllRequest);
        return ApiResponse.<ShowAllResponse<GroupResponse>>builder()
                .result(response)
                .build();
    }

    @GetMapping("/showInfo-my-group")
    @PreAuthorize("hasRole('STUDENT')")
    public ApiResponse<GroupResponse> showInfoMyGroup() {
        GroupResponse response = groupService.showInfoMyGroup();
        return ApiResponse.<GroupResponse>builder()
                .result(response)
                .build();
    }
}
