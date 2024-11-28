package jobs4u.base.network.responseprocessors;

import jobs4u.base.jobopeningmanagement.dto.JobOpeningDTO;
import jobs4u.base.network.SerializationUtil;
import jobs4u.base.network.data.DataBlock;
import jobs4u.base.network.data.DataDTO;

import java.util.ArrayList;
import java.util.List;

public class JobOpeningListResponseProcessor implements ResponseProcessor<JobOpeningDTO> {
    @Override
    public List<JobOpeningDTO> process(DataDTO dataDTO) {
        List<JobOpeningDTO> JobOpeningDTOList = new ArrayList<>();
        List<DataBlock> dataBlocks = dataDTO.dataBlockList();

        for (DataBlock dataBlock : dataBlocks) {
            JobOpeningDTO JobOpeningDTO = (JobOpeningDTO) SerializationUtil.deserialize(dataBlock.data());
            JobOpeningDTOList.add(JobOpeningDTO);
        }

        return JobOpeningDTOList;
    }
}
