package com.openfree.domain.model.user;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created by luokai on 17-7-13.
 */
public class UserInfo {

    @NotNull
    @Min(1)
    private Long id;
}
