package fii.request.manager.service.entityextractor.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class NamedEntityAttributes {
    private final String contextKey;
    private final String regex;
}
