use crate::Result;
use jni::{objects::JObject, sys::jlong, JNIEnv};

mod http;

fn get_pointer<'local>(env: &mut JNIEnv<'local>, object: &JObject<'local>) -> Result<jlong> {
    Ok(env.get_field(object, "pointer", "J")?.try_into()?)
}
