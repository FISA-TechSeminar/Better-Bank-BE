package com.practice.thebetterbank.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QInterestHistory is a Querydsl query type for InterestHistory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QInterestHistory extends EntityPathBase<InterestHistory> {

    private static final long serialVersionUID = -1331263818L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QInterestHistory interestHistory = new QInterestHistory("interestHistory");

    public final QAccount account;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Double> ihAmount = createNumber("ihAmount", Double.class);

    public final DatePath<java.time.LocalDate> ihDate = createDate("ihDate", java.time.LocalDate.class);

    public final QUser user;

    public QInterestHistory(String variable) {
        this(InterestHistory.class, forVariable(variable), INITS);
    }

    public QInterestHistory(Path<? extends InterestHistory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QInterestHistory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QInterestHistory(PathMetadata metadata, PathInits inits) {
        this(InterestHistory.class, metadata, inits);
    }

    public QInterestHistory(Class<? extends InterestHistory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.account = inits.isInitialized("account") ? new QAccount(forProperty("account"), inits.get("account")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

