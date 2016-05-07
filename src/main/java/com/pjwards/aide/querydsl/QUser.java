//package com.pjwards.aide.querydsl;
//
//import com.mysema.query.types.Path;
//import com.mysema.query.types.PathMetadata;
//import com.mysema.query.types.path.*;
//import com.pjwards.aide.domain.User;
//import com.pjwards.aide.domain.enums.Role;
//
//import javax.annotation.Generated;
//import java.util.Date;
//
//import static com.mysema.query.types.PathMetadataFactory.forVariable;
//
///**
// * QUser is a Querydsl query type for User
// */
//@Generated("com.mysema.query.codegen.EntitySerializer")
//public class QUser extends EntityPathBase<User> {
//
//    private static final long serialVersionUID = 799046687L;
//
//    private static final PathInits INITS = PathInits.DIRECT2;
//
//    public static final QUser user = new QUser("user");
//
//    public final QAssets assets;
//
//    public final DateTimePath<java.util.Date> createdDate = createDateTime("createdDate", java.util.Date.class);
//
//    public final StringPath email = createString("email");
//
//    public final NumberPath<Long> id = createNumber("id", Long.class);
//
//    public final DateTimePath<Date> lastDate = createDateTime("lastDate", java.util.Date.class);
//
//    public final StringPath name = createString("name");
//
//    public final StringPath password = createString("password");
//
//    public final EnumPath<Role> role = createEnum("role", Role.class);
//
//    public final StringPath company = createString("company");
//
//    public final StringPath description = createString("description");
//
//    public QUser(String variable) {
//        this(User.class, forVariable(variable), INITS);
//    }
//
//    public QUser(Path<? extends User> path) {
//        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
//    }
//
//    public QUser(PathMetadata<?> metadata) {
//        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
//    }
//
//    public QUser(PathMetadata<?> metadata, PathInits inits) {
//        this(User.class, metadata, inits);
//    }
//
//    public QUser(Class<? extends User> type, PathMetadata<?> metadata, PathInits inits) {
//        super(type, metadata, inits);
//        this.assets = inits.isInitialized("assets") ? new QAssets(forProperty("assets"), inits.get("assets")) : null;
//    }
//
//}