1. Efficiency Mechanisms
    Early exit from order processing loop
    Direct processing of orders i.e no intermediate collections and no new order instance.
    filtering only performed at end
2. Solution Approach
    using a failed fast approach
    Preserves original order objects and only modifies their quantities
    Handles partial fills through quantity reduction on existing orders
    Clear separation between matching logic (MatchingEngine) and order storage (OrderBook)
3. Data Structures
    List keeps order