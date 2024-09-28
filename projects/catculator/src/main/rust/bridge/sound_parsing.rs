use jni::{
    objects::{JClass, JString},
    sys::jlong,
    JNIEnv,
};

use crate::sound_parsing::audio_length;
use crate::Result;

fn parse_duration<'local>(env: &mut JNIEnv<'local>, path: &JString<'local>) -> Result<jlong> {
    let path: String = env.get_string(path)?.into();

    let duration = audio_length(path)?;

    Ok(duration.as_nanos().try_into()?)
}

#[no_mangle]
pub extern "system" fn Java_net_pixaurora_catculator_api_music_SoundFile_parseDuration0<'local>(
    mut env: JNIEnv<'local>,
    _class: JClass<'local>,
    path: JString<'local>,
) -> jlong {
    match parse_duration(&mut env, &path) {
        Ok(duration) => duration,
        Err(error) => {
            error.throw(&mut env);
            jlong::default()
        }
    }
}
