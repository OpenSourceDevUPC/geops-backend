package com.geopslabs.geops.backend.identity.infrastructure.hashing.bcrypt;

import com.geopslabs.geops.backend.identity.application.internal.outboundservices.hashing.HashingService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * This interface is a marker interface for the BCrypt hashing service.
 * It extends the {@link HashingService} and {@link PasswordEncoder} interfaces.
 */
public interface BCryptHashingService extends HashingService, PasswordEncoder {
}
