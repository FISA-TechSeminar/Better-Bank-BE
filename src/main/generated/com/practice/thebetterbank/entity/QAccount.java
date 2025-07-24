package com.practice.thebetterbank.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAccount is a Querydsl query type for Account
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAccount extends EntityPathBase<Account> {

    private static final long serialVersionUID = -1219548455L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAccount account = new QAccount("account");

    public final StringPath accountNumber = createString("accountNumber");

    public final NumberPath<Long> balance = createNumber("balance", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<InterestHistory, QInterestHistory> interestHistories = this.<InterestHistory, QInterestHistory>createList("interestHistories", InterestHistory.class, QInterestHistory.class, PathInits.DIRECT2);

    public final NumberPath<Double> interestRate = createNumber("interestRate", Double.class);

    public final StringPath name = createString("name");

    public final ListPath<TransactionHistory, QTransactionHistory> transactionHistories = this.<TransactionHistory, QTransactionHistory>createList("transactionHistories", TransactionHistory.class, QTransactionHistory.class, PathInits.DIRECT2);

    public final QUser user;

    public QAccount(String variable) {
        this(Account.class, forVariable(variable), INITS);
    }

    public QAccount(Path<? extends Account> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAccount(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAccount(PathMetadata metadata, PathInits inits) {
        this(Account.class, metadata, inits);
    }

    public QAccount(Class<? extends Account> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

