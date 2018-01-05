import java.util.*;
import java.util.HashSet;

class Content {
		public HashSet<Block> config;
		public HashSet<Position> empty;

		private boolean [] mark;
		private int len;
		private int width;

		public Content ( int l, int w) {
			config = new HashSet<Block> ();
			empty = new HashSet<Position> ();
			mark = new boolean [l * w];
			len = l;
			width = w;
		}

		@Override
		public int hashCode () {
			int code = 0;
			for (Block b: config)
				code += b.toString().hashCode();
			return code;
		}

		@Override
		public String toString () {
			String s = "";

			for (Block b: config) 
				s = s + "\n" + b;

			return s;
		}

		@Override
		public boolean equals (Object o) {
			Content c = (Content) o;
			for (Block b: c.config) {
				if (! config.contains (b))
					return false;
			}
			return true;
		}

		public void addBlock (Block b) {
			config.add ( b );

			// update empty positions;
			for (int i = b.row; i < b.row + b.len; i++)
				for (int j = b.col; j < b.col + b.width; j++)
					mark[i*width + j] = true;
		}

		public Content genNext (Block mv, int direction) {
			Content c = new Content (len, width);
			Block moved;

			for (Block b: config) {
				if (b.equals (mv)) {
					switch (direction) {
						case 1: moved = new Block (b.len, b.width, b.row, b.col+1);
								c.addBlock (moved);
							break;
						case 2: moved = new Block (b.len, b.width, b.row+1, b.col);
								c.addBlock (moved);
							break;
						case 3: moved = new Block (b.len, b.width, b.row, b.col-1);
								c.addBlock (moved);
							break;
						case 4: moved = new Block (b.len, b.width, b.row-1, b.col);
								c.addBlock (moved);
							break;
						default: System.out.println ("No SUCH DIRECTION!");
							System.exit (1);
					}
				}
				else c.addBlock (b);
			}

			c.calcEmpty();
			return c;
		}

		public HashSet<Position> calcEmpty () {
			for (int i = 0; i < len * width; i++ ) {
				if (mark[i] == false) {
					empty.add (new Position (i / width, i % width));
				}
			}
			return empty;
		}

		public boolean checkGoal (ArrayList<Block> goal) {
			boolean win = true;
			for (Block b: goal) {
				if ( !config.contains (b) ) {
					win = false;
				}
			}
			return win;
		}

		public void prtContent () {
			System.out.println ();
			for (Block b: config)
				System.out.println ("\t" + b);
			System.out.println ();
		}

		public static void main (String [] args) {
			Content c1 = new Content (2, 2);
			Content c2 = new Content (2, 2);

			Block b = new Block (1, 1, 0, 0);
			c1.addBlock (b);
			c2.addBlock (b);
			System.out.println ("block 1");
			c1.prtContent ();
			System.out.println ("block 2");
			c2.prtContent ();
			System.out.println ("equals: " + c1.equals(c2));

			System.out.println ("empty positions: ");
			for (Position p: c1.calcEmpty ())
				System.out.println (p);

			System.out.println ("Move block (1, 1, 0, 0) t0 South ");
			c1.genNext (b, 2).prtContent ();


		}
}