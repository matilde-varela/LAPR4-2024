package jobs4u.base.network.responseprocessors;

import jobs4u.base.applicationmanagement.dto.ApplicationDTO;
import jobs4u.base.network.SerializationUtil;
import jobs4u.base.network.data.DataBlock;
import jobs4u.base.network.data.DataDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ApplicationListResponseProcessor implements ResponseProcessor<Map.Entry<ApplicationDTO, Integer>> {
    @Override
    public List<Map.Entry<ApplicationDTO, Integer>> process(DataDTO dataDTO) {
        List<Map.Entry<ApplicationDTO, Integer>> applicationDTOList = new ArrayList<>();
        List<DataBlock> dataBlocks = dataDTO.dataBlockList();

        int counter = 1;
        ApplicationDTO key = null;
        Integer value = 0;
        for (DataBlock dataBlock : dataBlocks) {
            if (counter == 1) {
                key = (ApplicationDTO) SerializationUtil.deserialize(dataBlock.data());
                counter--;
            } else {
                value = (Integer) SerializationUtil.deserialize(dataBlock.data());
                counter++;
                Map.Entry<ApplicationDTO, Integer> entry = Map.entry(key, value);
                applicationDTOList.add(entry);
            }
        }
        return applicationDTOList;
    }
}
