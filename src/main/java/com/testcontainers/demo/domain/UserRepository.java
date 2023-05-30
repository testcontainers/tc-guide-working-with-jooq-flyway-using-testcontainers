package com.testcontainers.demo.domain;

import static com.testcontainers.demo.jooq.tables.Users.USERS;

import com.testcontainers.demo.jooq.tables.records.UsersRecord;
import java.time.LocalDateTime;
import java.util.Optional;
import org.jooq.DSLContext;
import org.jooq.RecordMapper;
import org.springframework.stereotype.Repository;

@Repository
class UserRepository {
    private final DSLContext dsl;

    UserRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    public User createUser(User user) {
        return this.dsl
                .insertInto(USERS)
                .set(USERS.NAME, user.name())
                .set(USERS.EMAIL, user.email())
                .set(USERS.CREATED_AT, LocalDateTime.now())
                .returning()
                // .fetchOne(record -> new User(record.getId(), record.getName(), record.getEmail()))
                .fetchOne(UserRecordMapper.INSTANCE);
    }

    public Optional<User> getUserByEmail(String email) {
        return this.dsl
                .selectFrom(USERS)
                .where(USERS.EMAIL.equalIgnoreCase(email))
                // .fetchOptional(record -> new User(record.getId(), record.getName(), record.getEmail()));
                .fetchOptional(UserRecordMapper.INSTANCE);
    }

    static class UserRecordMapper implements RecordMapper<UsersRecord, User> {
        public static final UserRecordMapper INSTANCE = new UserRecordMapper();

        private UserRecordMapper() {}

        @Override
        public User map(UsersRecord record) {
            return new User(record.getId(), record.getName(), record.getEmail());
        }
    }
}
