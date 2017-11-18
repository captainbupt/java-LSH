/*
 * The MIT License
 *
 * Copyright 2015 Thibault Debatty.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package info.debatty.java.lsh;

import java.io.Serializable;

/**
 * @author Weizhou He
 */
public class LSHSimHash extends LSH implements Serializable {
    private SimHash sh;

    /**
     * LSH implementation relying on SimHash, to bin vectors s times (stages)
     * in b buckets (per stage), in a space with 64 dimensions. Input vectors
     * with a low Hamming distance have a high probability of falling in the
     * same bucket...
     * <p>
     * Supported input types:
     * - double[]
     * - sparseIntegerVector
     * - int[]
     * - others to come...
     *
     * @param stages  stages
     * @param buckets buckets (per stage)
     */
    public LSHSimHash(
            final int stages, final int buckets) {

        super(stages, buckets);

        this.sh = new SimHash();
    }

    /**
     * Empty constructor, used only for serialization.
     */
    public LSHSimHash() {
    }

    /**s
     * Hash (bin) a vector in s stages into b buckets.
     *
     * @param vector vector
     * @return stages and buckets
     */
    public final int[] hash(final String[] vector) {
        return hashSignature(sh.signature(vector));
    }


    public SimHash getSh() {
        return sh;
    }
}
