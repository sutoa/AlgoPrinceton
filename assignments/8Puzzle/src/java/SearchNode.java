class SearchNode implements Comparable<SearchNode>{
   final SearchNode parent;
   final int moveCount;
   final Board board;
    private final int priority;

    SearchNode(Board board, SearchNode parent) {
       this.board = board;
       this.moveCount = parent == null ? 0 : parent.moveCount + 1;
       this.parent = parent;
       priority = board.manhattan() + moveCount;
   }

   int priority(){
       return priority;
   }

   @Override
   public int compareTo(SearchNode o) {
       return priority() - o.priority();
   }
}
