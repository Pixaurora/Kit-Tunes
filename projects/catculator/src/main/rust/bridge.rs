use crate::Result;
use jni::{objects::JObject, sys::jlong, JNIEnv};

mod http;
mod sound_parsing;

fn get_pointer<'local>(env: &mut JNIEnv<'local>, object: &JObject<'local>) -> Result<jlong> {
    Ok(env.get_field(object, "pointer", "J")?.try_into()?)
}
