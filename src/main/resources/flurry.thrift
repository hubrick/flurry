namespace java com.chronotrack.flurry.gen
namespace php Bazu.Flurry

exception GenerationError {
  1: string message,
}

struct IdDetailed {
  1: required i64 id;
  2: required i64 time;
  3: required i32 worker;
  4: required i32 sequence;
}

service Flurry {
  i64 get_worker_id()
  i64 get_id()
  IdDetailed get_id_detailed()
}