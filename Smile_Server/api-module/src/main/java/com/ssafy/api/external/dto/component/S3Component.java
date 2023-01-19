package com.ssafy.api.external.dto.component;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix="cloud.aws.s3")
public class S3Component {
    private String bucket;
}
