package net.pixaurora.kit_tunes.impl.config;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Function;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;

public interface SerialType<A extends SpecifiesType<A>> {
	public String name();
	public Codec<? extends A> codec();

	public static record Group<T extends SerialType<?>>(String typeName, List<T> types) {
		public Codec<T> typeCodec() {
			return Codec.STRING.comapFlatMap(this::lookupName, SerialType::name);
		}

		public <G> DataResult<T> lookupBy(String lookupType, Function<T, G> getter, G lookupObject) {
			Optional<T> foundType = this.types.stream()
					.filter(type -> getter.apply(type).equals(lookupObject))
					.findFirst();

			try {
				return DataResult.success(foundType.get());
			} catch (NoSuchElementException typeNotFound) {
				return DataResult.error(() -> String.format("%s lookup did not find a type with %s = `%s`", this.typeName, lookupType, lookupObject.toString()));
			}
		}

		public DataResult<T> lookupName(String name) {
			return this.lookupBy("name", SerialType::name, name);
		}
	}
}
