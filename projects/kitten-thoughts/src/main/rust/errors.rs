use std::fmt::Display;

use jni::errors::Error as JNIError;
use jni::JNIEnv;
use std::io::Error as IOError;

#[derive(Debug)]
pub enum Error {
    JNI(JNIError),
    IO(IOError),
}

pub type Result<T> = core::result::Result<T, Error>;

impl Error {
    pub fn throw(&self, env: &mut JNIEnv) {
        let a = env
            .throw_new("java/io/IOException", self.to_string())
            .unwrap();
    }
}

impl std::error::Error for Error {}

impl Display for Error {
    fn fmt(&self, f: &mut std::fmt::Formatter<'_>) -> std::fmt::Result {
        match self {
            Error::JNI(error) => error.fmt(f),
            Error::IO(error) => error.fmt(f),
        }
    }
}

impl From<JNIError> for Error {
    fn from(value: JNIError) -> Self {
        Error::JNI(value)
    }
}

impl From<IOError> for Error {
    fn from(value: IOError) -> Self {
        Error::IO(value)
    }
}
