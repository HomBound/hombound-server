package HOMBOUND.Controllers;

import HOMBOUND.Models.HomBoundRequest;
import HOMBOUND.Models.Item;
import HOMBOUND.Models.enums.HomBoundStatus;
import HOMBOUND.Models.enums.ItemType;
import HOMBOUND.Repositories.HomBoundRequestRepository;
import HOMBOUND.Repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
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
        checkForRequestErrors(requestId, item);
        item.setHomBoundRequest(request.get());
        return itemRepository.save(item);
    }

    @PutMapping(path="/{requestId}/item/{itemId}/update")
    public Item updateItemInRequest(@PathVariable("requestId") Long requestId, @PathVariable("itemId") Long itemId , @RequestBody Item item){
        Optional<HomBoundRequest> request = homBoundRequestRepository.findById(requestId);
        checkForRequestErrors(requestId, item);
        Optional<Item> itemToUpdate = itemRepository.findById(itemId);
        if(!itemToUpdate.isPresent()){
            throw new Error("Item with id " + itemId + " does not exist!");
        }
        if(item.getName() != null){
            itemToUpdate.get().setName(item.getName());
        }
        if(item.getCategory() != null){
            itemToUpdate.get().setCategory(item.getCategory());
        }
        if(item.getQuantity() != null){
            itemToUpdate.get().setQuantity(item.getQuantity());
        }
        return itemRepository.save(itemToUpdate.get());
    }

    public void checkForRequestErrors(Long requestId,Item item){
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
    }
}
