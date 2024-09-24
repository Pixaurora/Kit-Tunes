mod bridge;
pub mod errors;

pub mod http;
pub mod sound_parsing;

pub use errors::Error;
pub use errors::Result;

#[cfg(test)]
mod test;
