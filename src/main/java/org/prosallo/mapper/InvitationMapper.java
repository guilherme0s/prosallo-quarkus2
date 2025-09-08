package org.prosallo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.prosallo.data.InvitationResponse;
import org.prosallo.model.Invitation;

@Mapper(componentModel = MappingConstants.ComponentModel.CDI)
public interface InvitationMapper {

    InvitationResponse toResponse(Invitation invitation);
}
