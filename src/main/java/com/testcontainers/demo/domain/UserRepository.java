package com.testcontainers.demo.domain;

import com.testcontainers.demo.jooq.tables.Users;
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
                .insertInto(Users.USERS)
                .set(Users.USERS.NAME, user.name())
                .set(Users.USERS.EMAIL, user.email())
                .set(Users.USERS.CREATED_AT, LocalDateTime.now())
                .returning()
                // .fetchOne(record -> new User(record.getId(), record.getName(), record.getEmail()))
                .fetchOne(new UserRecordMapper());
    }

    public Optional<User> getUserByEmail(String email) {
        return this.dsl
                .selectFrom(Users.USERS)
                .where(Users.USERS.EMAIL.equalIgnoreCase(email))
                // .fetchOptional(record -> new User(record.getId(), record.getName(), record.getEmail()));
                .fetchOptional(new UserRecordMapper());
    }

    static class UserRecordMapper implements RecordMapper<UsersRecord, User> {
        @Override
        public User map(UsersRecord record) {
            return new User(record.getId(), record.getName(), record.getEmail());
        }
    }
}
