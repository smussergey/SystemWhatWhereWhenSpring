package ua.training.game.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_PLAYER, ROLE_REFEREE;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
