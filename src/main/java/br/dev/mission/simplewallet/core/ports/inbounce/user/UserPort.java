package br.dev.mission.simplewallet.core.ports.inbounce.user;

import java.util.List;

import br.dev.mission.simplewallet.core.model.user.UserCore;

public interface UserPort {
    void createUser(UserCore user);
    UserCore getUserById(Long id);
    void updateUser(UserCore user);
    void deleteUser(Long id);
    List<UserCore> getAllUsers();
}
