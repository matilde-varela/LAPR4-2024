package jobs4u.base.network.responseprocessors;

import jobs4u.base.network.data.DataDTO;

import java.util.List;

public interface ResponseProcessor<T> {
    List<T> process(DataDTO dataDTO);
}
