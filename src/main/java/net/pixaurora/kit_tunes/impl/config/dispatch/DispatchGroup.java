package net.pixaurora.kit_tunes.impl.config.dispatch;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Function;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;

public record DispatchGroup<A extends SpecifiesType<A>, T extends DispatchType<A>>(String typeName, List<T> types) {
	public Codec<DispatchType<A>> typeCodec() {
		return Codec.STRING.comapFlatMap(this::lookupName, DispatchType::name);
	}

	public Codec<A> dispatchCodec() {
		return this.typeCodec().dispatchStable(A::type, DispatchType::codec);
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
		return this.lookupBy("name", DispatchType::name, name);
	}
}
