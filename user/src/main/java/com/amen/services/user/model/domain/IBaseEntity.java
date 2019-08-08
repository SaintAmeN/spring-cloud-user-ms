package com.aps.services.user.model.domain;

import com.aps.services.user.model.common.AbstractResponse;

/**
 * @author amen
 * @project user
 * @created 05.07.19
 */
public interface IBaseEntity {
    Long getId();


    public static AbstractResponse createUpdateResponse(IBaseEntity entity) {
        return AbstractResponse.builder().id(entity.getId()).message("Entity updated successfully.").build();
    }
}
