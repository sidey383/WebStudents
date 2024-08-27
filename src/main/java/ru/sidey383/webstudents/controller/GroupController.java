package ru.sidey383.webstudents.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import ru.sidey383.webstudents.entity.Group;
import ru.sidey383.webstudents.mapper.GroupMapper;
import ru.sidey383.webstudents.model.CreateGroup;
import ru.sidey383.webstudents.model.GroupResponse;
import ru.sidey383.webstudents.model.PageData;
import ru.sidey383.webstudents.model.UpdateGroup;
import ru.sidey383.webstudents.repository.GroupRepository;

import java.util.List;

@RestController
@AllArgsConstructor
public class GroupController {

    public static final String GROUP_PATH = "/api/group";

    public static final String GROUP_ID_PATH = "/api/group/{id}";

    private final GroupRepository groupRepository;

    private final GroupMapper groupMapper;

    @PostMapping(GROUP_PATH)
    public GroupResponse createGroup(
            @RequestBody
            CreateGroup createGroup
    ) {
        Group group = groupMapper.map(createGroup);
        groupRepository.save(group);
        group.setStudentCount(0L);
        return groupMapper.map(group);
    }

    @GetMapping(GROUP_PATH)
    public PageData<GroupResponse> getGroups(
            @RequestParam(defaultValue = "10", required = false)
            int pageSize,
            @RequestParam(defaultValue = "1", required = false)
            int pageNumber
    ) {
        Page<Group> page = groupRepository.findAll(PageRequest.of(pageNumber, pageSize));
        List<GroupResponse> responsesData = page.stream().map(groupMapper::map).toList();
        return new PageData<>(responsesData, page.getSize(), page.getTotalPages(), page.getNumber());
    }

    @DeleteMapping(GROUP_ID_PATH)
    public void deleteGroup(
            @PathVariable
            long id
    ) {
        groupRepository.deleteById(id);
    }

    @PatchMapping(GROUP_ID_PATH)
    public GroupResponse updateGroup(
            @PathVariable
            long id,
            @RequestBody
            UpdateGroup updateGroup
    ) {
        return groupMapper.map(groupRepository.updateNumber(id, updateGroup.number()));
    }

}
