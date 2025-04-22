package com.example.myEcommerceAPI.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.example.myEcommerceAPI.entity.Tag;
import com.example.myEcommerceAPI.repository.TagRepository;

public class TagValidationService {
    private final TagRepository tagRepository;

    public TagValidationService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<Tag> validateAndFetchTags(List<Long> tagIds) {
        List<Tag> tags = tagRepository.findAllById(tagIds);

        if (tags.size() != tagIds.size()) {
            Set<Long> foundIds = tags.stream()
                    .map(Tag::getId)
                    .collect(Collectors.toSet());

            List<Long> missingIds = tagIds.stream()
                    .filter(id -> !foundIds.contains(id))
                    .collect(Collectors.toList());

            throw new IllegalArgumentException("Invalid tag IDs: " + missingIds);
        }

        return tags;
    }
}
