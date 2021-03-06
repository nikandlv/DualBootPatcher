// automatically generated, do not modify

package mbtool.daemon.v3;

import java.nio.*;
import java.lang.*;
import java.util.*;
import com.google.flatbuffers.*;

@SuppressWarnings("unused")
public final class PathGetDirectorySizeResponse extends Table {
  public static PathGetDirectorySizeResponse getRootAsPathGetDirectorySizeResponse(ByteBuffer _bb) { return getRootAsPathGetDirectorySizeResponse(_bb, new PathGetDirectorySizeResponse()); }
  public static PathGetDirectorySizeResponse getRootAsPathGetDirectorySizeResponse(ByteBuffer _bb, PathGetDirectorySizeResponse obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__init(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public PathGetDirectorySizeResponse __init(int _i, ByteBuffer _bb) { bb_pos = _i; bb = _bb; return this; }

  public boolean success() { int o = __offset(4); return o != 0 ? 0!=bb.get(o + bb_pos) : false; }
  public String errorMsg() { int o = __offset(6); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer errorMsgAsByteBuffer() { return __vector_as_bytebuffer(6, 1); }
  public long size() { int o = __offset(8); return o != 0 ? bb.getLong(o + bb_pos) : 0; }

  public static int createPathGetDirectorySizeResponse(FlatBufferBuilder builder,
      boolean success,
      int error_msgOffset,
      long size) {
    builder.startObject(3);
    PathGetDirectorySizeResponse.addSize(builder, size);
    PathGetDirectorySizeResponse.addErrorMsg(builder, error_msgOffset);
    PathGetDirectorySizeResponse.addSuccess(builder, success);
    return PathGetDirectorySizeResponse.endPathGetDirectorySizeResponse(builder);
  }

  public static void startPathGetDirectorySizeResponse(FlatBufferBuilder builder) { builder.startObject(3); }
  public static void addSuccess(FlatBufferBuilder builder, boolean success) { builder.addBoolean(0, success, false); }
  public static void addErrorMsg(FlatBufferBuilder builder, int errorMsgOffset) { builder.addOffset(1, errorMsgOffset, 0); }
  public static void addSize(FlatBufferBuilder builder, long size) { builder.addLong(2, size, 0); }
  public static int endPathGetDirectorySizeResponse(FlatBufferBuilder builder) {
    int o = builder.endObject();
    return o;
  }
};

