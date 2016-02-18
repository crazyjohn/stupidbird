// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: Human.proto

package com.stone.proto;

public final class Humans {
  private Humans() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
  }
  /**
   * Protobuf enum {@code SceneObjectType}
   */
  public enum SceneObjectType
      implements com.google.protobuf.ProtocolMessageEnum {
    /**
     * <code>MONSTER = 1;</code>
     */
    MONSTER(0, 1),
    /**
     * <code>HUMAN = 2;</code>
     */
    HUMAN(1, 2),
    /**
     * <code>NPC = 3;</code>
     */
    NPC(2, 3),
    ;

    /**
     * <code>MONSTER = 1;</code>
     */
    public static final int MONSTER_VALUE = 1;
    /**
     * <code>HUMAN = 2;</code>
     */
    public static final int HUMAN_VALUE = 2;
    /**
     * <code>NPC = 3;</code>
     */
    public static final int NPC_VALUE = 3;


    public final int getNumber() { return value; }

    public static SceneObjectType valueOf(int value) {
      switch (value) {
        case 1: return MONSTER;
        case 2: return HUMAN;
        case 3: return NPC;
        default: return null;
      }
    }

    public static com.google.protobuf.Internal.EnumLiteMap<SceneObjectType>
        internalGetValueMap() {
      return internalValueMap;
    }
    private static com.google.protobuf.Internal.EnumLiteMap<SceneObjectType>
        internalValueMap =
          new com.google.protobuf.Internal.EnumLiteMap<SceneObjectType>() {
            public SceneObjectType findValueByNumber(int number) {
              return SceneObjectType.valueOf(number);
            }
          };

    public final com.google.protobuf.Descriptors.EnumValueDescriptor
        getValueDescriptor() {
      return getDescriptor().getValues().get(index);
    }
    public final com.google.protobuf.Descriptors.EnumDescriptor
        getDescriptorForType() {
      return getDescriptor();
    }
    public static final com.google.protobuf.Descriptors.EnumDescriptor
        getDescriptor() {
      return com.stone.proto.Humans.getDescriptor().getEnumTypes().get(0);
    }

    private static final SceneObjectType[] VALUES = values();

    public static SceneObjectType valueOf(
        com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
      if (desc.getType() != getDescriptor()) {
        throw new java.lang.IllegalArgumentException(
          "EnumValueDescriptor is not for this type.");
      }
      return VALUES[desc.getIndex()];
    }

    private final int index;
    private final int value;

    private SceneObjectType(int index, int value) {
      this.index = index;
      this.value = value;
    }

    // @@protoc_insertion_point(enum_scope:SceneObjectType)
  }

  public interface HumanOrBuilder extends
      // @@protoc_insertion_point(interface_extends:Human)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>required int64 guid = 1;</code>
     */
    boolean hasGuid();
    /**
     * <code>required int64 guid = 1;</code>
     */
    long getGuid();

    /**
     * <code>required int64 playerId = 2;</code>
     */
    boolean hasPlayerId();
    /**
     * <code>required int64 playerId = 2;</code>
     */
    long getPlayerId();

    /**
     * <code>required string name = 3;</code>
     */
    boolean hasName();
    /**
     * <code>required string name = 3;</code>
     */
    java.lang.String getName();
    /**
     * <code>required string name = 3;</code>
     */
    com.google.protobuf.ByteString
        getNameBytes();

    /**
     * <code>required int32 level = 4;</code>
     */
    boolean hasLevel();
    /**
     * <code>required int32 level = 4;</code>
     */
    int getLevel();
  }
  /**
   * Protobuf type {@code Human}
   */
  public static final class Human extends
      com.google.protobuf.GeneratedMessage implements
      // @@protoc_insertion_point(message_implements:Human)
      HumanOrBuilder {
    // Use Human.newBuilder() to construct.
    private Human(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
      super(builder);
      this.unknownFields = builder.getUnknownFields();
    }
    private Human(boolean noInit) { this.unknownFields = com.google.protobuf.UnknownFieldSet.getDefaultInstance(); }

    private static final Human defaultInstance;
    public static Human getDefaultInstance() {
      return defaultInstance;
    }

    public Human getDefaultInstanceForType() {
      return defaultInstance;
    }

    private final com.google.protobuf.UnknownFieldSet unknownFields;
    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
        getUnknownFields() {
      return this.unknownFields;
    }
    private Human(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      initFields();
      int mutable_bitField0_ = 0;
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
            case 8: {
              bitField0_ |= 0x00000001;
              guid_ = input.readInt64();
              break;
            }
            case 16: {
              bitField0_ |= 0x00000002;
              playerId_ = input.readInt64();
              break;
            }
            case 26: {
              com.google.protobuf.ByteString bs = input.readBytes();
              bitField0_ |= 0x00000004;
              name_ = bs;
              break;
            }
            case 32: {
              bitField0_ |= 0x00000008;
              level_ = input.readInt32();
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e.getMessage()).setUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.stone.proto.Humans.internal_static_Human_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.stone.proto.Humans.internal_static_Human_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.stone.proto.Humans.Human.class, com.stone.proto.Humans.Human.Builder.class);
    }

    public static com.google.protobuf.Parser<Human> PARSER =
        new com.google.protobuf.AbstractParser<Human>() {
      public Human parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new Human(input, extensionRegistry);
      }
    };

    @java.lang.Override
    public com.google.protobuf.Parser<Human> getParserForType() {
      return PARSER;
    }

    private int bitField0_;
    public static final int GUID_FIELD_NUMBER = 1;
    private long guid_;
    /**
     * <code>required int64 guid = 1;</code>
     */
    public boolean hasGuid() {
      return ((bitField0_ & 0x00000001) == 0x00000001);
    }
    /**
     * <code>required int64 guid = 1;</code>
     */
    public long getGuid() {
      return guid_;
    }

    public static final int PLAYERID_FIELD_NUMBER = 2;
    private long playerId_;
    /**
     * <code>required int64 playerId = 2;</code>
     */
    public boolean hasPlayerId() {
      return ((bitField0_ & 0x00000002) == 0x00000002);
    }
    /**
     * <code>required int64 playerId = 2;</code>
     */
    public long getPlayerId() {
      return playerId_;
    }

    public static final int NAME_FIELD_NUMBER = 3;
    private java.lang.Object name_;
    /**
     * <code>required string name = 3;</code>
     */
    public boolean hasName() {
      return ((bitField0_ & 0x00000004) == 0x00000004);
    }
    /**
     * <code>required string name = 3;</code>
     */
    public java.lang.String getName() {
      java.lang.Object ref = name_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        if (bs.isValidUtf8()) {
          name_ = s;
        }
        return s;
      }
    }
    /**
     * <code>required string name = 3;</code>
     */
    public com.google.protobuf.ByteString
        getNameBytes() {
      java.lang.Object ref = name_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        name_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int LEVEL_FIELD_NUMBER = 4;
    private int level_;
    /**
     * <code>required int32 level = 4;</code>
     */
    public boolean hasLevel() {
      return ((bitField0_ & 0x00000008) == 0x00000008);
    }
    /**
     * <code>required int32 level = 4;</code>
     */
    public int getLevel() {
      return level_;
    }

    private void initFields() {
      guid_ = 0L;
      playerId_ = 0L;
      name_ = "";
      level_ = 0;
    }
    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      if (!hasGuid()) {
        memoizedIsInitialized = 0;
        return false;
      }
      if (!hasPlayerId()) {
        memoizedIsInitialized = 0;
        return false;
      }
      if (!hasName()) {
        memoizedIsInitialized = 0;
        return false;
      }
      if (!hasLevel()) {
        memoizedIsInitialized = 0;
        return false;
      }
      memoizedIsInitialized = 1;
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      getSerializedSize();
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        output.writeInt64(1, guid_);
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        output.writeInt64(2, playerId_);
      }
      if (((bitField0_ & 0x00000004) == 0x00000004)) {
        output.writeBytes(3, getNameBytes());
      }
      if (((bitField0_ & 0x00000008) == 0x00000008)) {
        output.writeInt32(4, level_);
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;

      size = 0;
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt64Size(1, guid_);
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt64Size(2, playerId_);
      }
      if (((bitField0_ & 0x00000004) == 0x00000004)) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(3, getNameBytes());
      }
      if (((bitField0_ & 0x00000008) == 0x00000008)) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(4, level_);
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    private static final long serialVersionUID = 0L;
    @java.lang.Override
    protected java.lang.Object writeReplace()
        throws java.io.ObjectStreamException {
      return super.writeReplace();
    }

    public static com.stone.proto.Humans.Human parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.stone.proto.Humans.Human parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.stone.proto.Humans.Human parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.stone.proto.Humans.Human parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.stone.proto.Humans.Human parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static com.stone.proto.Humans.Human parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }
    public static com.stone.proto.Humans.Human parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input);
    }
    public static com.stone.proto.Humans.Human parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input, extensionRegistry);
    }
    public static com.stone.proto.Humans.Human parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static com.stone.proto.Humans.Human parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }

    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(com.stone.proto.Humans.Human prototype) {
      return newBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() { return newBuilder(this); }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code Human}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:Human)
        com.stone.proto.Humans.HumanOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return com.stone.proto.Humans.internal_static_Human_descriptor;
      }

      protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.stone.proto.Humans.internal_static_Human_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.stone.proto.Humans.Human.class, com.stone.proto.Humans.Human.Builder.class);
      }

      // Construct using com.stone.proto.Humans.Human.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessage.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
        }
      }
      private static Builder create() {
        return new Builder();
      }

      public Builder clear() {
        super.clear();
        guid_ = 0L;
        bitField0_ = (bitField0_ & ~0x00000001);
        playerId_ = 0L;
        bitField0_ = (bitField0_ & ~0x00000002);
        name_ = "";
        bitField0_ = (bitField0_ & ~0x00000004);
        level_ = 0;
        bitField0_ = (bitField0_ & ~0x00000008);
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(buildPartial());
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.stone.proto.Humans.internal_static_Human_descriptor;
      }

      public com.stone.proto.Humans.Human getDefaultInstanceForType() {
        return com.stone.proto.Humans.Human.getDefaultInstance();
      }

      public com.stone.proto.Humans.Human build() {
        com.stone.proto.Humans.Human result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public com.stone.proto.Humans.Human buildPartial() {
        com.stone.proto.Humans.Human result = new com.stone.proto.Humans.Human(this);
        int from_bitField0_ = bitField0_;
        int to_bitField0_ = 0;
        if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
          to_bitField0_ |= 0x00000001;
        }
        result.guid_ = guid_;
        if (((from_bitField0_ & 0x00000002) == 0x00000002)) {
          to_bitField0_ |= 0x00000002;
        }
        result.playerId_ = playerId_;
        if (((from_bitField0_ & 0x00000004) == 0x00000004)) {
          to_bitField0_ |= 0x00000004;
        }
        result.name_ = name_;
        if (((from_bitField0_ & 0x00000008) == 0x00000008)) {
          to_bitField0_ |= 0x00000008;
        }
        result.level_ = level_;
        result.bitField0_ = to_bitField0_;
        onBuilt();
        return result;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof com.stone.proto.Humans.Human) {
          return mergeFrom((com.stone.proto.Humans.Human)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.stone.proto.Humans.Human other) {
        if (other == com.stone.proto.Humans.Human.getDefaultInstance()) return this;
        if (other.hasGuid()) {
          setGuid(other.getGuid());
        }
        if (other.hasPlayerId()) {
          setPlayerId(other.getPlayerId());
        }
        if (other.hasName()) {
          bitField0_ |= 0x00000004;
          name_ = other.name_;
          onChanged();
        }
        if (other.hasLevel()) {
          setLevel(other.getLevel());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public final boolean isInitialized() {
        if (!hasGuid()) {
          
          return false;
        }
        if (!hasPlayerId()) {
          
          return false;
        }
        if (!hasName()) {
          
          return false;
        }
        if (!hasLevel()) {
          
          return false;
        }
        return true;
      }

      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        com.stone.proto.Humans.Human parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (com.stone.proto.Humans.Human) e.getUnfinishedMessage();
          throw e;
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int bitField0_;

      private long guid_ ;
      /**
       * <code>required int64 guid = 1;</code>
       */
      public boolean hasGuid() {
        return ((bitField0_ & 0x00000001) == 0x00000001);
      }
      /**
       * <code>required int64 guid = 1;</code>
       */
      public long getGuid() {
        return guid_;
      }
      /**
       * <code>required int64 guid = 1;</code>
       */
      public Builder setGuid(long value) {
        bitField0_ |= 0x00000001;
        guid_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>required int64 guid = 1;</code>
       */
      public Builder clearGuid() {
        bitField0_ = (bitField0_ & ~0x00000001);
        guid_ = 0L;
        onChanged();
        return this;
      }

      private long playerId_ ;
      /**
       * <code>required int64 playerId = 2;</code>
       */
      public boolean hasPlayerId() {
        return ((bitField0_ & 0x00000002) == 0x00000002);
      }
      /**
       * <code>required int64 playerId = 2;</code>
       */
      public long getPlayerId() {
        return playerId_;
      }
      /**
       * <code>required int64 playerId = 2;</code>
       */
      public Builder setPlayerId(long value) {
        bitField0_ |= 0x00000002;
        playerId_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>required int64 playerId = 2;</code>
       */
      public Builder clearPlayerId() {
        bitField0_ = (bitField0_ & ~0x00000002);
        playerId_ = 0L;
        onChanged();
        return this;
      }

      private java.lang.Object name_ = "";
      /**
       * <code>required string name = 3;</code>
       */
      public boolean hasName() {
        return ((bitField0_ & 0x00000004) == 0x00000004);
      }
      /**
       * <code>required string name = 3;</code>
       */
      public java.lang.String getName() {
        java.lang.Object ref = name_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          if (bs.isValidUtf8()) {
            name_ = s;
          }
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>required string name = 3;</code>
       */
      public com.google.protobuf.ByteString
          getNameBytes() {
        java.lang.Object ref = name_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          name_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>required string name = 3;</code>
       */
      public Builder setName(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000004;
        name_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>required string name = 3;</code>
       */
      public Builder clearName() {
        bitField0_ = (bitField0_ & ~0x00000004);
        name_ = getDefaultInstance().getName();
        onChanged();
        return this;
      }
      /**
       * <code>required string name = 3;</code>
       */
      public Builder setNameBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000004;
        name_ = value;
        onChanged();
        return this;
      }

      private int level_ ;
      /**
       * <code>required int32 level = 4;</code>
       */
      public boolean hasLevel() {
        return ((bitField0_ & 0x00000008) == 0x00000008);
      }
      /**
       * <code>required int32 level = 4;</code>
       */
      public int getLevel() {
        return level_;
      }
      /**
       * <code>required int32 level = 4;</code>
       */
      public Builder setLevel(int value) {
        bitField0_ |= 0x00000008;
        level_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>required int32 level = 4;</code>
       */
      public Builder clearLevel() {
        bitField0_ = (bitField0_ & ~0x00000008);
        level_ = 0;
        onChanged();
        return this;
      }

      // @@protoc_insertion_point(builder_scope:Human)
    }

    static {
      defaultInstance = new Human(true);
      defaultInstance.initFields();
    }

    // @@protoc_insertion_point(class_scope:Human)
  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_Human_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_Human_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\013Human.proto\"D\n\005Human\022\014\n\004guid\030\001 \002(\003\022\020\n\010" +
      "playerId\030\002 \002(\003\022\014\n\004name\030\003 \002(\t\022\r\n\005level\030\004 " +
      "\002(\005*2\n\017SceneObjectType\022\013\n\007MONSTER\020\001\022\t\n\005H" +
      "UMAN\020\002\022\007\n\003NPC\020\003B\031\n\017com.stone.protoB\006Huma" +
      "ns"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
    internal_static_Human_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_Human_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessage.FieldAccessorTable(
        internal_static_Human_descriptor,
        new java.lang.String[] { "Guid", "PlayerId", "Name", "Level", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
