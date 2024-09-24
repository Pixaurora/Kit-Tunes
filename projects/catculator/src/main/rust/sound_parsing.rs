use std::{path::Path, time::Duration};

use crate::Result;
use lofty::{file::AudioFile, probe::Probe};

pub fn audio_length<P: AsRef<Path>>(audio_path: P) -> Result<Duration> {
    let audio_file = Probe::open(audio_path)?.read()?;

    let properties = audio_file.properties();

    Ok(properties.duration())
}
