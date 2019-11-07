require 'erb'
# Reading the Federalist Papers
#
# Fed object with attibutes:
# fedNum  - Federalist Number
# fedTitle - Title of Paper
# fedPub - Publisher of Paper
# fedAuthor - Author of Paper
#
class Fed
    attr_accessor :fedNum
    attr_accessor :fedTitle
    attr_accessor :fedPub
    attr_accessor :fedAuthor
    attr_accessor :fedDate
    # Constructor
    def initialize
      @fedNum=0
      @fedTitle=""
      @fedPub=""
      @fedAuthor=""
      @fedDate=""
    end

    # Method to print data on one Fed object
    # Obsolete
    def prt
       puts "Federalist #{@fedNum}"
       puts "Title: #{@fedTitle}"
       puts "Pub: #{@fedPub}"
       puts "Author: #{@fedAuthor}"
       puts "Date: #{@fedDate}"
       puts "\n"
    end

    # Method that returns the binding of the object.
    # This is needed for an ERB file.
    def get_binding
      binding
    end

    # Method to clean up the look of the data
    def clean_up(curFed)
      clean_author(curFed)
      clean_title_pub(curFed)
    end

    # Start private methods

    #helper method to clean author name
    private def clean_author(curFed)
      words = curFed.fedAuthor.split() #split the words up
      if words.size==1 then   #if its only one name
        curFed.fedAuthor.capitalize!
        curFed.fedAuthor = add_first_name(curFed.fedAuthor)

      else
        words[0].capitalize!    #capitalize the last name
        words[0] = add_first_name(words[0])
        words[1].downcase!
        words[2].capitalize!    #capitalize the last name
        words[2] = add_first_name(words[2])
        curFed.fedAuthor = words.join(' ')  #join them back with a space
      end
    end

    #helper method to clean title and publisher
    private def clean_title_pub(curFed)
      titleLines = curFed.fedTitle.lines.map(&:chomp) #split the title into lines
      i = 1
      until i > 2
        if ((titleLines.last.include? "For") || (titleLines.last.include? "From")) then #if publisher is last line
          curFed.fedPub = titleLines.last
          # Take out the words For/From and the
          curFed.fedPub.gsub!("For ", "")
          curFed.fedPub.gsub!("From ", "")
          curFed.fedPub.gsub!("the ", "")
          #curFed.fedPub = titleLines.last
          pubList = curFed.fedPub.split(".")
          # Check if list contains a publisher and date
          curFed.fedPub = pubList[0]
          if(curFed.fedDate.empty?) then
            curFed.fedDate = pubList[1]
          end
          titleLines.pop #take the publisher out of the title
          curFed.fedTitle = titleLines.join("\n") # put the title back together
          break
        elsif ((titleLines.last.include? "Tuesday") || (titleLines.last.include? "Wednesday") || (titleLines.last.include? "Thursday") || (titleLines.last.include? "Friday") || (titleLines.last.include? "January")) then
          #if the last line is the date, set the date and pop it out.
          curFed.fedDate = titleLines.last
          curFed.fedDate.gsub!(".", "")
          titleLines.pop
          i = i+1
        else #if there is no publisher, join it back
          curFed.fedTitle = titleLines.join("\n")
          break
        end
      end
    end

    #helper method to add the first name to the author
    private def add_first_name(last_name)
      case(last_name)
      when("Hamilton")
          last_name = "Alexander " + last_name
        when("Jay")
          last_name = "John " + last_name
        when("Madison")
          last_name = "James " + last_name
      end
      last_name
    end
end

#=========================
# Main program
#=========================

# Input will come from file fed.txt
file = File.new("fed.txt", "r")

# List of Fed objects
feds = []
curFed = nil
readstate = 'b4Fed'
# Read and process each line
while (line = file.gets)
    line.strip!            # Remove trailing white space
    words = line.split     # Split into array of words

    if curFed.nil?
      # "FEDERALIST No. number" starts a new Fed object
      if (readstate=='b4Fed' && words[0]=="FEDERALIST") then
         curFed = Fed.new    # Construct new Fed object
         feds << curFed      # Add it to the array
         curFed.fedNum = words[2] # The number is always in the second posistion
         readstate = 'b4TitlePub' # change the state of the program
         next
      end
    end

    if !curFed.nil?

      if(readstate=='b4TitlePub' && !words.empty?)
        curFed.fedTitle = words.join(' ') # Add the title to the string
        readstate = 'inTitlePub' # change the state

        next
      end

      if(readstate=='inTitlePub' && !words.empty?)
        curFed.fedTitle = curFed.fedTitle + "\n" + words.join(' ') # add more title to the string for multiline titles
        next
      end

      if(readstate=='inTitlePub' && words.empty?)
        readstate = 'b4Author'  # if the line is empty, change the state
        next
      end

      if(readstate=='b4Author' && !words.empty?)
        # Error checking
        if(words[0] == "For" || words[0] == "From") then
          curFed.fedTitle = curFed.fedTitle + "\n" + words.join(' ')
          next
        end
        # SET author
        curFed.fedAuthor = words.join(' ')
        readstate = 'b4Fed'

        # Clean up title, publisher, and author strings
        curFed.clean_up(curFed)

        # Set object to nil
        curFed = nil
        next
      end

    end

end # End of reading

file.close # close the file



# Apply the prt (print) method to each Fed object in the feds array
#feds.each{|f| f.prt}
# Create a new ERB template (read from file template.html.erb)
template = File.read("./template.html.erb")
# Create a new ERB object
renderer = ERB.new(template)

# Open a new file to store the html in
File.open('fed.html', 'w+' ) do |temp|
  # Write the heading info for the HTML file
  temp.write("<!DOCTYPE html>

  <html>
  <head>
    <title>Federalist Index</title>
    <link rel=\"stylesheet\" href=\"styles.css\" >
  </head>
  <body>
    <h3>Federalist Index</h3>
    <table>
      <tr>
        <th>No.</th>
        <th>Author</th>
        <th>Title</th>
        <th>Pub</th>
        <th>Date</th>
      </tr>")

  # Append the information from each fed object
  feds.each{|f| temp << renderer.result(f.get_binding)}
  # Append the end of the HTML file information
  temp << ("
    </table>
  </body>
</html>")

end # end reading

system("open fed.html") # Open the HTML file (MAC ONLY)
