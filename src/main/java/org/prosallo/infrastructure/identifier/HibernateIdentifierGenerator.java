package org.prosallo.infrastructure.identifier;

import org.hibernate.engine.spi.SharedSessionContractImplementor;

public class HibernateIdentifierGenerator implements org.hibernate.id.IdentifierGenerator {

    private final IdentifierGenerator identifierGenerator = new TsidIdentifierGenerator();

    @Override
    public Object generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) {
        return identifierGenerator.generateIdentifier();
    }
}
