1. Efficiency Mechanisms
    TreeMap allows O(log n) insertion, deletion, and lookup of price levels
    Maintains sorted price levels automatically
    Enables quick navigation between different price points
    Linklist is a dynamic data structure therefore no memory wastage
    Improved insertion and deletion compared to ArrayList
    HashMap provides O(1) order retrieval by ID
    Allows quick look up of orders instead of transversing two maps
    Optional improves code clarity and safety, though it introduces slight performance overhead.
 2. Solution Approach
 There are distinct buy and selling of the orderbooks. The advantages of this are the following
 1. Separation of concerns
 2. Allows for independent optimisation,future scaling and modifications.
    Flexible order management system by having unique Id. This enables easy tracking.
    Support for adding,deleting and modifying orders.
    Automatic priority Reset on orders
3. Data Structures
a) TreeMap
    Provides automatic sorted storage of price levels
    Supports both ascending (sell) and descending (buy) ordering through comparators
    Logarithmic time complexity ensures scalability
b) LinkList
 It has a dynamic size, making it ideal for unpredictable data growth.
 Insertions and deletions are efficient, especially at the beginning or middle, as only pointers are updated.
 It doesn’t waste memory since it allocates space only for existing nodes.
 LinkedList is great for implementing stacks and queues due to efficient operations at both ends.
 Additionally, it handles frequent shifts, like removing the first element, in constant time (O(1)), unlike ArrayList, which requires shifting elements (O(n)).
 These features make it suitable for dynamic, insertion-heavy, and queue-like use cases.
c)HashMap
It offers constant-time performance (O(1)) for basic operations like insertion, deletion, and retrieval, making it highly efficient for large datasets.
 a HashMap optimizes for speed rather than maintaining any specific order.
 Its ability to handle large datasets efficiently with minimal memory overhead makes it a go-to choice for caching and lookup-intensive applications.


