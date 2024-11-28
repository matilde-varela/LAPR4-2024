package jobs4u.base.network.responseprocessors;

import jobs4u.base.network.SerializationUtil;
import jobs4u.base.network.data.DataBlock;
import jobs4u.base.network.data.DataDTO;
import jobs4u.base.notificationmanagement.dto.NotificationDTO;

import java.util.ArrayList;
import java.util.List;

public class NotificationListResponseProcessor implements ResponseProcessor<NotificationDTO> {
    @Override
    public List<NotificationDTO> process(DataDTO dataDTO) {
        List<NotificationDTO> NotificationDTOList = new ArrayList<>();
        List<DataBlock> dataBlocks = dataDTO.dataBlockList();

        for (DataBlock dataBlock : dataBlocks) {
            NotificationDTO notificationDTO = (NotificationDTO) SerializationUtil.deserialize(dataBlock.data());
            NotificationDTOList.add(notificationDTO);
        }

        return NotificationDTOList;
    }
}
