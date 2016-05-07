//package com.pjwards.aide.querydsl;
//
//import com.mysema.query.types.Path;
//import com.mysema.query.types.PathMetadata;
//import com.mysema.query.types.path.EntityPathBase;
//import com.mysema.query.types.path.NumberPath;
//import com.mysema.query.types.path.PathInits;
//import com.mysema.query.types.path.StringPath;
//import com.pjwards.aide.domain.Assets;
//
//import javax.annotation.Generated;
//
//import static com.mysema.query.types.PathMetadataFactory.forVariable;
//
///**
// * QAssets is a Querydsl query type for Assets
// */
//@Generated("com.mysema.query.codegen.EntitySerializer")
//public class QAssets extends EntityPathBase<Assets> {
//
//    private static final long serialVersionUID = -1487454505L;
//
//    private static final PathInits INITS = PathInits.DIRECT2;
//
//    public static final QAssets assets = new QAssets("assets");
//
//    public final NumberPath<Integer> downloadCount = createNumber("downloadCount", Integer.class);
//
//    public final StringPath fileName = createString("fileName");
//
//    public final NumberPath<Long> fileSize = createNumber("fileSize", Long.class);
//
//    public final NumberPath<Long> id = createNumber("id", Long.class);
//
//    public final StringPath realPath = createString("realPath");
//
//    public final QUser user;
//
//    public QAssets(String variable) {
//        this(Assets.class, forVariable(variable), INITS);
//    }
//
//    public QAssets(Path<? extends Assets> path) {
//        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
//    }
//
//    public QAssets(PathMetadata<?> metadata) {
//        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
//    }
//
//    public QAssets(PathMetadata<?> metadata, PathInits inits) {
//        this(Assets.class, metadata, inits);
//    }
//
//    public QAssets(Class<? extends Assets> type, PathMetadata<?> metadata, PathInits inits) {
//        super(type, metadata, inits);
//        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
//    }
//
//}