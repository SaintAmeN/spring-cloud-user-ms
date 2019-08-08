package com.amen.services.user.model.domain;

import com.amen.services.user.model.common.AbstractResponse;

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
