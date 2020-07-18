package HOMBOUND.Controllers;

import HOMBOUND.Models.HomBoundRequest;
import HOMBOUND.Models.Item;
import HOMBOUND.Models.enums.HomBoundStatus;
import HOMBOUND.Models.enums.ItemType;
import HOMBOUND.Repositories.HomBoundRequestRepository;
import HOMBOUND.Repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Optional;

@CrossOrigin
@RequestMapping("api/requests")
@RestController
public class ItemController {
    @Autowired
    public ItemRepository itemRepository;

    @Autowired
    public HomBoundRequestRepository homBoundRequestRepository;

    @PostMapping(path="/{requestId}/add")
    public Item addItemToRequest(@PathVariable("requestId") Long requestId, @RequestBody Item item){
        Optional<HomBoundRequest> request = homBoundRequestRepository.findById(requestId);
        if (!request.isPresent()) {
            String errMsg = "Could not find HomBound Request with id " + requestId + "!";
            throw new Error(errMsg);
        }
        if (!request.get().getStatus().equals(HomBoundStatus.Open.name())) {
            String errMsg = "Request " + requestId + " has already been submitted and can no longer take new items.";
            throw new Error(errMsg);
        }
        ItemType[] possibleItemOptions = ItemType.values();
        ItemType itemType = ItemType.valueOf(item.getCategory());
        if (!Arrays.asList(possibleItemOptions).contains(itemType)) {
            String errMsg = "Item type " + item.getCategory() + " does not exist.";
            throw new Error(errMsg);
        }
        item.setHomBoundRequest(request.get());
        return itemRepository.save(item);
    }
}
