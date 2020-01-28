package nl.quintor.app.transformer;

import nl.quintor.app.dto.UserAccountDTO;
import nl.quintor.app.dto.UserRegistrationDTO;
import nl.quintor.app.model.UserAccountModel;

/***
 * also contains UserRegistrationDTO to model and vicaversa.
 */
public class UserTransformer implements Transformer<UserAccountModel, UserAccountDTO> {

    @Override
    public UserAccountModel toModel(UserAccountDTO dto) {
        UserAccountModel userAccountModel = new UserAccountModel();
        userAccountModel.setCity(dto.getCity());
        userAccountModel.setCountry(dto.getCountry());
        userAccountModel.setEmail(dto.getEmail());
        userAccountModel.setName(dto.getName());
        userAccountModel.setNumber(dto.getNumber());
        userAccountModel.setPhoneNumber(dto.getPhoneNumber());
        userAccountModel.setStreet(dto.getStreet());
        userAccountModel.setSurname(dto.getSurname());
        userAccountModel.setUsername(dto.getUsername());
        userAccountModel.setRoleModels(dto.getRoleModels());

        return userAccountModel;
    }

    @Override
    public UserAccountDTO toDto(UserAccountModel model) {
        UserAccountDTO userAccountDto = new UserAccountDTO();
        userAccountDto.setCity(model.getCity());
        userAccountDto.setCountry(model.getCountry());
        userAccountDto.setEmail(model.getEmail());
        userAccountDto.setName(model.getName());
        userAccountDto.setNumber(model.getNumber());
        userAccountDto.setPhoneNumber(model.getPhoneNumber());
        userAccountDto.setStreet(model.getStreet());
        userAccountDto.setSurname(model.getSurname());
        userAccountDto.setUsername(model.getUsername());
        userAccountDto.setRoleModels(model.getRoleModels());

        return userAccountDto;
    }

    public UserRegistrationDTO userAccountModelToUserRegistrationDTO(UserAccountModel model){
        UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO();
        userRegistrationDTO.setCity(model.getCity());
        userRegistrationDTO.setPassword(model.getPassword());
        userRegistrationDTO.setCountry(model.getCountry());
        userRegistrationDTO.setEmail(model.getEmail());
        userRegistrationDTO.setName(model.getName());
        userRegistrationDTO.setNumber(model.getNumber());
        userRegistrationDTO.setPhoneNumber(model.getPhoneNumber());
        userRegistrationDTO.setStreet(model.getStreet());
        userRegistrationDTO.setSurname(model.getSurname());
        userRegistrationDTO.setUsername(model.getUsername());

        return userRegistrationDTO;
    }

    public UserAccountModel userRegistrationDTOToUserAccountModel(UserRegistrationDTO model){
        UserAccountModel userAccountModel = new UserAccountModel();
        userAccountModel.setCity(model.getCity());
        userAccountModel.setPassword(model.getPassword());
        userAccountModel.setCountry(model.getCountry());
        userAccountModel.setEmail(model.getEmail());
        userAccountModel.setName(model.getName());
        userAccountModel.setNumber(model.getNumber());
        userAccountModel.setPhoneNumber(model.getPhoneNumber());
        userAccountModel.setStreet(model.getStreet());
        userAccountModel.setSurname(model.getSurname());
        userAccountModel.setUsername(model.getUsername());
        userAccountModel.setRoleModels(model.getRoleModels());

        return userAccountModel;
    }

}
