use std::fmt::Display;

use jni::errors::Error as JNIError;
use jni::JNIEnv;
use lofty::error::LoftyError;
use rocket::Error as RocketError;
use std::io::Error as IOError;
use tokio::sync::mpsc::error::TryRecvError;
use tokio::task::JoinError;

#[derive(Debug)]
pub enum Error {
    JNI(JNIError),
    IO(IOError),
    Rocket(RocketError),
    Recv(TryRecvError),
    Join(JoinError),
    Server(String),
    Lofty(LoftyError),
}

pub type Result<T> = core::result::Result<T, Error>;

impl Error {
    pub fn throw(&self, env: &mut JNIEnv) {
        if let Err(error) = env.throw_new("java/io/IOException", self.to_string()) {
            eprintln!("Couldn't throw exception due to an error! {}", error);
        }
    }
}

impl std::error::Error for Error {}

impl Display for Error {
    fn fmt(&self, f: &mut std::fmt::Formatter<'_>) -> std::fmt::Result {
        match self {
            Error::JNI(error) => error.fmt(f),
            Error::IO(error) => error.fmt(f),
            Error::Rocket(error) => error.fmt(f),
            Error::Recv(error) => error.fmt(f),
            Error::Join(error) => error.fmt(f),
            Error::Server(message) => write!(f, "Server Error: {}", message),
            Error::Lofty(error) => error.fmt(f),
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

impl From<RocketError> for Error {
    fn from(value: RocketError) -> Self {
        Error::Rocket(value)
    }
}

impl From<JoinError> for Error {
    fn from(value: JoinError) -> Self {
        Error::Join(value)
    }
}

impl From<TryRecvError> for Error {
    fn from(value: TryRecvError) -> Self {
        Error::Recv(value)
    }
}

impl From<LoftyError> for Error {
    fn from(value: LoftyError) -> Self {
        Error::Lofty(value)
    }
}
