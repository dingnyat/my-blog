package ding.nyat.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Pair<F, S> {
    public F first;
    public S second;
}
