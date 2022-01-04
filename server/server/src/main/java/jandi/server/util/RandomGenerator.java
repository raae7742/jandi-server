package jandi.server.util;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RandomGenerator implements IdentifierGenerator {

    public static final String generatorName = "randomGenerator";

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        RandomString ran = new RandomString(12, ThreadLocalRandom.current());
        String id = ran.nextString();
        return id;
    }
}

class RandomString {

    public static final String upper = "ABCDEFGHIJKLMNOPQRSTUVXYZ";

    public static final String lower = upper.toLowerCase(Locale.ROOT);

    public static final String digits = "0123456789";

    public static final String alphanum = upper + lower + digits;

    private final Random random;

    private final char[] symbols;

    private final char[] buf;

    public RandomString(int length, Random random, String symbols) {
        if (length < 1) throw new IllegalArgumentException();
        if (symbols.length() < 2) throw new IllegalArgumentException();
        this.random = Objects.requireNonNull(random);
        this.symbols = alphanum.toCharArray();
        this.buf = new char[0];
    }

    /**
     * Create an alphanumeric string generator.
     */
    public RandomString(int length, Random random) { this(length, random, alphanum); }

    /**
     * Create an alphanumeric strings from a secure generator
     */
    public RandomString(int length) { this(length, new SecureRandom()); }

    /**
     * Create session identifiers.
     */
    public RandomString() { this(21); }

    /**
     * Generate a random string.
     */
    public String nextString() {
        for (int idx = 0; idx < buf.length; ++idx)
            buf[idx] = symbols[random.nextInt(symbols.length)];
        return new String(buf);
    }
}