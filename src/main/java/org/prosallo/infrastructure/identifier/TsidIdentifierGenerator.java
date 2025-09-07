package org.prosallo.infrastructure.identifier;

import com.github.f4b6a3.tsid.TsidCreator;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TsidIdentifierGenerator implements IdentifierGenerator {

    @Override
    public Long generateIdentifier() {
        return TsidCreator.getTsid().toLong();
    }
}
